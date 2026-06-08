package com.project.saas.entity.master;

import com.project.saas.enums.ComplaintStatus;
import com.project.saas.enums.WorkType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assign_work",schema = "public")
public class AssignWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_electrician_id", referencedColumnName = "id", nullable = false)
    private User assignedElectrician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customers_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkType workType;

    @Column(nullable = false)
    private String workDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus complaintStatus;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

}
