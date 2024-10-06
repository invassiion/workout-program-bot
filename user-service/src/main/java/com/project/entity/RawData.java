package com.project.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.telegram.telegrambots.meta.api.objects.Update;

@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Convert(attributeName = "entityAttrName", converter = JsonBinaryType.class)
@Table(name = "raw_data")
public class RawData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Update event;

}
