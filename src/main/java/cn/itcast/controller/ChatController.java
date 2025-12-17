package cn.itcast.controller;

import cn.itcast.dto.ChatDTO;
import cn.itcast.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final VectorStore vectorStore;

    /**
     * 处理流式聊天请求，返回服务区发送事件（SSE）格式的响应流
     *
     * @param chatDTO 用户输入的聊天问题
     * @return 包含逐条聊天响应的响应式数据流，通过 Server-Sent Event 协议传输
     */
    @PostMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatDTO chatDTO) {
        return chatService.chatStream(chatDTO.getQuestion(), chatDTO.getSessionId());
    }

    /**
     * 搜索向量数据库
     *
     * @param query 搜索关键字
     */
    @PostMapping("/search")
    public List<Document> search(@RequestParam("query") String query) {
        return this.vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .build());
    }
}
