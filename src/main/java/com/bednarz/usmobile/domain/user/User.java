package com.bednarz.usmobile.domain.user;

import com.bednarz.usmobile.infrastructure.audit.AuditInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Builder
@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditInfo {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String password;
}