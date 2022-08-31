package com.lastrix.scp.writeservice.web.v1;


import com.lastrix.scp.core.rest.Rest;
import com.lastrix.scp.core.rest.jwt.Jwt;
import com.lastrix.scp.writeservice.service.EnrolleeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollee")
public class EnrolleeController {
    @Autowired
    private Jwt jwt;
    @Autowired
    private EnrolleeService srv;

    @PostMapping("/{spec}")
    public Rest<Boolean> enroll(@PathVariable UUID spec) {
        return Rest.of(srv.enroll(jwt.getUserId(), spec, null));
    }

    @PutMapping("/{spec}")
    public Rest<Boolean> confirm(@PathVariable UUID spec) {
        return Rest.of(srv.confirm(jwt.getUserId(), spec, null));
    }

    @DeleteMapping("/{spec}")
    public Rest<Boolean> cancel(@PathVariable UUID spec) {
        return Rest.of(srv.cancel(jwt.getUserId(), spec, null));
    }
}
