package umc.spring.domain.Terms.data;

import jakarta.persistence.*;
import lombok.*;
import umc.spring.global.data.BaseEntity;
import umc.spring.domain.Terms.data.mapping.MemberAgree;

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
   private List<MemberAgree> memberAgreeList = new ArrayList<>();
}