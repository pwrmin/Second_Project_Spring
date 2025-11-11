package com.kedu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Oracle Trigger/Sequence 사용
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private ChatRoom room;

    @Column(name = "SENDER", nullable = false)
    private String sender;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private MessageType type;

    @Column(name = "SEND_TIME", nullable = false)
    private LocalDateTime sendTime;

    //  DB에 저장 안하고 계산해서 내려주는 값
    @Transient
    private int readCount;

    @PrePersist
    protected void onCreate() {
        if (sendTime == null) sendTime = LocalDateTime.now();
        if (type == null) type = MessageType.TALK;
    }

    public enum MessageType {
        ENTER, TALK, LEAVE, SYSTEM, FILE
    }
}
