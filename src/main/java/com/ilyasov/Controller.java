package com.ilyasov;

import com.ilyasov.dao.AdvertisementDAO;
import com.ilyasov.entity.Advertisement;
import com.ilyasov.service.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        AdvertisementDAO dao = new AdvertisementDAO();
        Service service = new Service();
        List<Advertisement> advertisements = service.getAllAdvertisements();
        dao.putAllAdvertisements(advertisements);
        model.addAttribute("list", advertisements);
        return "index";
    }

    @RequestMapping(value = "/rows", method = RequestMethod.GET)
    public String getRowsCount(Model model) {
        AdvertisementDAO dao = new AdvertisementDAO();
        Service service = new Service();
        List<Advertisement> advertisements = service.getAllAdvertisements();
        int beforeRowsCount = dao.countRows();
        dao.putAllAdvertisements(advertisements);
        int afterRowsCount = dao.countRows();
        model.addAttribute("beforeRowsCount", beforeRowsCount);
        model.addAttribute("afterRowsCount", afterRowsCount);
        if (beforeRowsCount > afterRowsCount) {
            return "rows";
        } else return null;
    }
}
