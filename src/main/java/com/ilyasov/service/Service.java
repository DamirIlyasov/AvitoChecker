package com.ilyasov.service;

import com.ilyasov.entity.Advertisement;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> list = new ArrayList<Advertisement>();
        String htmlCode = null;
        try {
            htmlCode = readHtmlCodeFromPage("https://www.avito.ru/rossiya/avtomobili/subaru/baja");

            //regEx and creating entities
            Pattern pattern = Pattern.compile("Subaru&#x20;Baja,&#x20;(\\d{4}).*?class=\"about\">\\s*(\\d+(\\s*\\d*)*) руб" +
                    ".*?class=\"param\">(.+?)</span>.*?class=\"data\">(\\s*<p>(.+?)</p>)*?<p>(.+?)</p>" +
                    ".*?date c-2\">(.+?)</div>");
            Matcher matcher = pattern.matcher(htmlCode);


            while (matcher.find()) {
                Advertisement advertisement = new Advertisement();
                advertisement.setYear(Integer.parseInt(matcher.group(1)));
//                String price = matcher.group(2);
//                String replacedPrice = price.replaceAll(" ","");
//                int priceInt = Integer.parseInt(replacedPrice);
                advertisement.setPrice(Integer.parseInt(matcher.group(2).replaceAll(" ","")));  // Deleting spaces between numerals
                advertisement.setDescription((matcher.group(4)).replaceAll("&nbsp;", ""));
                    advertisement.setCity(matcher.group(7));

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm");
                String dateInString = matcher.group(8);
                Pattern yesterdayCheckPattern = Pattern.compile("\\s*Вчера\\s(\\d+?):(\\d+?)\\s");
                Pattern todayCheckPattern = Pattern.compile("\\s*Сегодня\\s(\\d+?):(\\d+?)\\s");
                Matcher yesterdayCheckMatcher = yesterdayCheckPattern.matcher(dateInString);
                Matcher todayCheckMatcher = yesterdayCheckPattern.matcher(dateInString);
                Date date;
                if (yesterdayCheckMatcher.matches()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(matcher.group(1)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(2)));
                    calendar.add(Calendar.DATE, -1);
                    date = calendar.getTime();
                    advertisement.setCreatedAt(date);
                }
                if (todayCheckMatcher.matches()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(matcher.group(1)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(2)));
                    date = calendar.getTime();
                    advertisement.setCreatedAt(date);
                } else {
                    date = formatter.parse(dateInString);
                    formatter.format(date);
                    advertisement.setCreatedAt(date);

                }
                list.add(advertisement);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    String readHtmlCodeFromPage(String adress) throws IOException {
        String s;
        URL url;
        InputStream inputStream = null;
        DataInputStream dataInputStream;
        StringBuilder stringBuilder = new StringBuilder();
        url = new URL(adress);
        inputStream = url.openStream();
        dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
        while ((s = dataInputStream.readLine()) != null) {
            stringBuilder.append(s);
        }
        String htmlCode = new String(stringBuilder.toString().getBytes("ISO-8859-1"), "UTF-8");
        dataInputStream.close();
        inputStream.close();
        return htmlCode;
    }
}
