package cn.itcast.embedding;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 城市信息向量化处理组件
 * 在应用启动时将城市数据转换为向量并储存到向量数据库
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RuleDataEmbedding {

    /**
     * 向量存储服务，用于持久化文档向量
     */
    private final VectorStore vectorStore;

    /**
     * 资源路径 (classpath:rules.json)
     */
    @Value("classpath:rules.json")
    private Resource resource;

    /**
     * 应用启动时初始化方法
     * 1. 读取数据文件
     * 2. 将文档向量化并储存
     */
    @PostConstruct
    public void init() throws Exception {
        // 1. 创建文本读取器并加载文件内容
        JsonReader jsonReader = new JsonReader(this.resource, "type", "condition", "description");
        List<Document> documentList = jsonReader.get();

        // 2. 将处理后的文档向量化并存入向量存储器
        this.vectorStore.add(documentList);
        log.info("将数据写入向量库成功，数据条数 {}", documentList.size());
    }
}
