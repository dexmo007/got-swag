package com.dexmohq.gotswag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author Henrik Drefs
 */
@Data
@ApiModel(description = "All information about the WebHook")
public class WebHook {

    @ApiModelProperty(notes = "A UUID that is generated upon creation of a new WebHook")
    private String id;

    @ApiModelProperty(notes = "The notification target URL. Must be a valid URL", required = true)
    @URL
    @NotNull
    private String target;

    @ApiModelProperty(notes = "The types to subscribe to. A empty set means to subscribe to all types of events.")
    private Set<EventType> types = Collections.emptySet();

}
