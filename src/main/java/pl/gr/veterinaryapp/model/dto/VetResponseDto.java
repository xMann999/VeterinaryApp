package pl.gr.veterinaryapp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetTime;

@Data
public class VetResponseDto {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private OffsetTime workStartTime;
    @NotNull
    private OffsetTime workEndTime;
}

