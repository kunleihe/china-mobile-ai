package cn.itcast.service;

import cn.itcast.service.ChatService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import cn.itcast.constants.Constant;
import cn.hutool.core.date.DateUtil;

import javax.naming.directory.SearchResult;

@lombok.extern.slf4j.Slf4j
@Slf4j
@Service
@RequiredArgsConstructor // Lombok 自动生成一个带所有 final 字段的构造方法。
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    /**
     * 处理用户问题并返回流式响应内容
     * 
     * @param question 用户输入的问题内容
     * @return 包含逐条响应内容和结束标记的响应流，每个元素为字符串格式
     */
    @Override
    public Flux<String> chatStream(String question, String sessionId) {
        // 创建搜索请求，用于搜索相关文档
        var searchRequest = SearchRequest.builder()
                .query(question) // 设置查询条件
                .topK(3) // 设置最多返回的文档数量
                .build();

        // 调用聊天客户端生成流式响应内容
        return this.chatClient.prompt()
                .system(Constant.SYSTEM_ROLE) // 设置系统角色
                .system(prompt -> prompt.param("now", DateUtil.now())) // 设置系统角色参数
                // 设置会话记忆参数
                .advisors(
                        advisor -> advisor.advisors(new QuestionAnswerAdvisor(vectorStore, searchRequest))
                                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, sessionId))
                .user(question)
                .stream()
                .content()
                // 记录每次收到的响应内容
                .doOnNext(content -> log.info("question: {}, content: {}", question, content))
                // 在流结束时结束标记
                .concatWith(Flux.just("[END]"));
    }
}
