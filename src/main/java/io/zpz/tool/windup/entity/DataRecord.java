package io.zpz.tool.windup.entity;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Builder
@Setter
@Getter
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@NoArgsConstructor
@AllArgsConstructor
public class DataRecord {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 从什么url上爬取下来的
     */
    private String url;

    /**
     * 描述
     */
    private String description;


    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String, String> content;


}
