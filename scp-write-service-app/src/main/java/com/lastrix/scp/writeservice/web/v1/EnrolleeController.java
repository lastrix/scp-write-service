package com.lastrix.scp.writeservice.web.v1;


import com.lastrix.scp.lib.rest.Rest;
import com.lastrix.scp.lib.rest.authz.RequireRoles;
import com.lastrix.scp.lib.rest.jwt.Jwt;
import com.lastrix.scp.writeservice.model.EnrolleeInfo;
import com.lastrix.scp.writeservice.service.EnrolleeService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/enrollee")
public class EnrolleeController {
    @Autowired
    private Jwt jwt;
    @Autowired
    private EnrolleeService srv;

    @RequireRoles("USER")
    @Timed(value = "enrollee_info")
    @GetMapping
    @Schema(description = "Get full information about your selections")
    public Rest<EnrolleeInfo> info() {
        return Rest.of(srv.getInfo(jwt.getUserId(), null));
    }

    @RequireRoles("USER")
    @Timed(value = "enrollee_enroll")
    @PostMapping("/{spec}")
    @Schema(description = "Enroll to speciality, enrolling to same speciality multiple times gives false, status won't be changed")
    public Rest<Boolean> enroll(@PathVariable UUID spec) {
        return Rest.of(srv.enroll(jwt.getUserId(), spec, null));
    }

    @RequireRoles("USER")
    @Timed(value = "enrollee_confirm")
    @PutMapping("/{spec}")
    @Schema(description = "Confirm your application to speciality, you can not confirm cancelled application or if you're not applied to spec")
    public Rest<Boolean> confirm(@PathVariable UUID spec) {
        return Rest.of(srv.confirm(jwt.getUserId(), spec, null));
    }

    @RequireRoles("USER")
    @Timed(value = "enrollee_cancel")
    @DeleteMapping("/{spec}")
    @Schema(description = "Cancel your application to speciality, you won't be able to interact with this spec afterwards rendering this spec non-existent to you")
    public Rest<Boolean> cancel(@PathVariable UUID spec) {
        return Rest.of(srv.cancel(jwt.getUserId(), spec, null));
    }
}
