package cn.itcast.service;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChatServiceTest {

    @Resource
    ChatService chatService;

    @Test
    void chat() {
        this.chatService.chat("讲一个笑话", "1234");
        this.chatService.chat("java是什么", "1235");
    }
}
