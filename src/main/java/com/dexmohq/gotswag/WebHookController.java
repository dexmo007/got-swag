package com.dexmohq.gotswag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Henrik Drefs
 */
@RestController
@RequestMapping("/webhook")
@Api(value = "webhooks")
public class WebHookController {

    private Map<String, WebHook> hooks = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new WebHook", notes = "Only a single WebHook is allowed per target")
    public WebHook create(@RequestParam("target") String target, @RequestParam("types") Set<EventType> types) {
        final WebHook hook = new WebHook();
        hook.setId(UUID.randomUUID().toString());
        hook.setTarget(target);
        hook.setTypes(types);
        hooks.put(hook.getId(), hook);
        return hook;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebHook> getById(@PathVariable("id") String id) {
        return Optional.ofNullable(hooks.get(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        hooks.remove(id);
    }

    @GetMapping
    public Collection<WebHook> getAll() {
        return hooks.values();
    }

    @GetMapping("/secret")
    @ApiOperation(value = "Internal publish endpoint", hidden = true)
    public Map<String, String> secret() {
        return Map.of("value", "secret");
    }

}
