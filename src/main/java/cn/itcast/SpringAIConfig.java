package cn.itcast;

import cn.itcast.constants.Constant;
import cn.itcast.tools.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringAIConfig {

    /**
     * 创建并返回一个 ChatClient 的 Spring Bean实例
     *
     * @param builder 用于构建 ChatClient 实例的构建者对象
     * @return 构建好的 ChatClient 实例
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 Advisor simpleLoggerAdvisor,
                                 Advisor promptChatMemoryAdvisor,
                                 Advisor safeGuardAdvisor,
                                 WeatherTools packageDataTools) {
        return builder
                .defaultSystem(Constant.SYSTEM_ROLE) // 设置默认系统角色
                .defaultAdvisors(simpleLoggerAdvisor, promptChatMemoryAdvisor, safeGuardAdvisor) // 设置默认的advisor
                .defaultTools(packageDataTools) // 设置默认的Tool
                .build();
    }

    /**
     * 创建并返回一个SimpleLoggerAdvisor的Spring Bean实例
     */
    @Bean
    public Advisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor();
    }

    /**
     * 创建并返回聊天记忆管理器的Spring Beam（基于内存实现）
     *
     * @return InMemoryChatMemory 实例，用于存储上下文信息
     */
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * 创建并返回聊天记忆管理advisor的Spring Bean
     *
     * @param chatMemory 聊天记忆管理器实例
     * @return MessageChatMemoryAdvisor 实例，用于在聊天过程中维护上下文
     */
    @Bean
    public Advisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
        return new MessageChatMemoryAdvisor(chatMemory);
    }

    /**
     * 创建并返回聊天记忆管理器advisor的Spring Bean实例
     *
     * @param chatMemory 聊天记忆管理器实例
     * @return PromptChatMemoryAdvisor 实例，用于在聊天过程中维护上下文
     */
    @Bean
    public Advisor promptChatMemoryAdvisor(ChatMemory chatMemory) {
        return new PromptChatMemoryAdvisor(chatMemory);
    }

    /**
     * 创建并返回一个敏感词校验advisor的Spring Bean实例
     *
     * @return SafeGuardAdvisor实例
     */
    @Bean
    public Advisor safeGuardAdvisor() {
        // 敏感词列表（示例数据）
        List<String> sensitiveWords = List.of("敏感词1", "敏感词2");
        // 创建安全防护Advisor，params以此为：敏感词库，违规提示语，advisor处理优先级（字数越小越优先）
        return new SafeGuardAdvisor(
                sensitiveWords,
                "敏感词提示：请勿输入敏感词！",
                Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER
        );
    }

    /**
     * 创建并返回一个 VectorStore 的 Spring Bean 实例
     *
     * @param embeddingModel 向量模型
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}
