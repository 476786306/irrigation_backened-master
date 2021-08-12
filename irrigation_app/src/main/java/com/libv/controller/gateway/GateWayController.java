package com.libv.controller.gateway;

import com.libv.gateway.GateWayService;
import com.libv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/gateway")
public class GateWayController {

    @Autowired
    GateWayService gateWayService;

    @PostMapping("/{id}")
    public R addGateway(@PathVariable long id) {
        return gateWayService.addGateway(id);
    }
}
