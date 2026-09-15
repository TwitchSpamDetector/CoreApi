package com.twitchspamdetector.coreapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequest {

    @NotBlank(message = "channelId es requerido")
    private String channelId;

    @NotBlank(message = "displayName es requerido")
    private String displayName;
}
