package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.dto.WordDTO;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

/**
 * ExcelService
 * <p>
 * Created at 20:11, 26 May 2023
 *
 * @author Zihlu WANG
 */
public interface ExcelService {

    /**
     * 解析上传的 Excel 文件
     *
     * @param file 文件
     * @return 被上传的单词
     */
    List<WordDTO> resolveMultipartFile(MultipartFile file) throws IOException;

}
