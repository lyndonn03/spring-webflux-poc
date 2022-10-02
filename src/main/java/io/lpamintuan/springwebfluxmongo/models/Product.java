package io.lpamintuan.springwebfluxmongo.models;

import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Name must not be empty.")
    private String name;

    @NotEmpty(message = "Code must not be empty.")
    @Size(min = 3, message = "Code must have size of atleast 3.")
    private String code;
    
}
