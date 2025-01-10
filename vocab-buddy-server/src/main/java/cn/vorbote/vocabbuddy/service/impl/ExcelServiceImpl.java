package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.constant.ContentType;
import cn.vorbote.vocabbuddy.model.dto.WordDTO;
import cn.vorbote.vocabbuddy.model.dto.WordDescriptionDTO;
import cn.vorbote.vocabbuddy.service.ExcelService;
import cn.vorbote.web.exceptions.BizException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ExcelServiceImpl
 * <p>
 * Created at 20:12, 26 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {


    @Override
    public List<WordDTO> resolveMultipartFile(MultipartFile file) throws IOException {
        final var fileContentType = file.getContentType();
        if (!ContentType.MICROSOFT_EXCEL.equalsIgnoreCase(fileContentType) && !ContentType.MICROSOFT_EXCEL_OPEN_XML.equalsIgnoreCase(fileContentType)) {
            throw new BizException(HttpServletResponse.SC_BAD_REQUEST, "不支持的文件类型！");
        }

        // 读区 Excel 数据
        final var workbook = new XSSFWorkbook(file.getInputStream());
        final var wordsSheet = workbook.getSheetAt(0);

        // 创建单词列表
        final var words = new HashMap<String, WordDTO>();

        // 获取总行数
        final var rowCount = wordsSheet.getLastRowNum();

        // 遍历每一行
        for (var rowIndex = 1; rowIndex <= rowCount; rowIndex++) { // 忽略标题行
            final var row = wordsSheet.getRow(rowIndex);

            var currentIndex = rowIndex + 1;
            var word = Optional.ofNullable(row.getCell(0))
                    .map(XSSFCell::getStringCellValue)
                    .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST,
                            "文件中存在未知的单词，其坐标为A%d".formatted(currentIndex)));

            var wordClass = Optional.ofNullable(row.getCell(1))
                    .map(XSSFCell::getNumericCellValue)
                    .map(Double::intValue)
                    .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "词性不能为空！"));

            var wordDescription = new WordDescriptionDTO()
                    .setWordClass(wordClass)
                    .setPhonetics(Optional.ofNullable(row.getCell(2))
                            .map(XSSFCell::getStringCellValue)
                            .orElse(null))
                    .setMeaning(Optional.ofNullable(row.getCell(3))
                            .map(XSSFCell::getStringCellValue)
                            .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "单词含义不能为空！")));

            var currentWord = words.get(word);
            if (Objects.isNull(currentWord)) {
                words.put(word, new WordDTO()
                        .setWord(word)
                        .addWordDescription(wordDescription));
            } else {
                currentWord.addWordDescription(wordDescription);
            }
        }
        workbook.close();

        return words.values().stream().toList();
    }
}
