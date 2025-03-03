package umc.spring.domain.terms.data;

import jakarta.persistence.*;
import lombok.*;
import umc.spring.global.data.BaseEntity;
import umc.spring.domain.terms.data.mapping.UserAgree;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private Boolean optional;

    @Builder.Default
   @OneToMany(mappedBy = "terms", cascade = CascadeType.ALL)
   private List<UserAgree> userAgreeList = new ArrayList<>();
}