package io.lpamintuan.springwebfluxmongo.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotNull
    @NotEmpty(message = "Name must not be null.")
    private String name;

    @NotNull
    @NotEmpty(message = "Code must not be null.")
    @Size(min = 3, message = "Code must have size of atleast 3.")
    private String code;
    
}
