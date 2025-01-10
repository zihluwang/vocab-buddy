package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.constant.GenerationMode;
import cn.vorbote.vocabbuddy.model.proto.Dictation;
import cn.vorbote.vocabbuddy.model.vo.DictationVO;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Random;

/**
 * DictationService
 * <p>
 * Created at 15:37, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
public interface DictationService extends IService<Dictation> {
    /**
     * 生成听写测试
     *
     * @param dictation 听写测试
     * @return 生成出的听写测试
     */
    DictationVO generateDictation(Dictation dictation);

    /**
     * 加载词库
     *
     * @param generationMode 生成模式
     * @param tagId          词库ID，若 {@code generationMode} 的值为 {@link GenerationMode#INCORRECT}，那么该数据不会生效
     * @param libSize        词库大小，若 {@code generationMode} 的值为 {@link GenerationMode#INCORRECT}，那么该数据不会生效
     * @param userId         用户的 ID，若 {@code generationMode} 的值为 {@link GenerationMode#RANDOM}，那么该数据不会生效
     * @return 生成的词库
     */
    List<WordVO> loadWordsLibrary(GenerationMode generationMode, Long tagId, Integer libSize, Long userId);

    /**
     * 字符串乱序
     *
     * @param str 将要被打乱的字符串
     * @return 乱序后的字符串
     */
    default String upset(String str) {
        var arr = str.toCharArray();
        var r = new Random();
        for (var i = 0; i < arr.length; i++) {   // 从0索引开始打乱数组
            var index = r.nextInt(arr.length);  // 形成随机索引
            var temp = arr[i];                 // 中间变量
            arr[i] = arr[index];                // 随机索引的数组赋值给当前循环的数组
            arr[index] = temp;                  // 值互换
        }
        str = new String(arr);                  // 把数组转换成字符串
        return str;                            // 返回字符串
    }

    /**
     * 检查听写的所属权
     *
     * @param dictationId 听写测试ID
     * @param userId      用户ID
     * @return 若该听写测试属于该用户，则返回 {@code true}；否则返回 {@code false}
     */
    boolean checkOwnership(Long dictationId, Long userId);

    /**
     * 检测听写测试是否已经结束
     *
     * @param dictationId 被检测的听写测试ID
     * @return 如果听写已停止，返回 {@code true}；否则返回 {@code false}
     */
    boolean isDictationStopped(Long dictationId);

    /**
     * 停止听写测试
     *
     * @param dictationId 被停止的听写测试ID
     * @return 被停止的听写测试
     */
    DictationVO stopDictation(Long dictationId);

    /**
     * 判断听写测试是否存在
     *
     * @param dictationId 听写测试ID
     * @return 如果听写测试存在，则返回 {@code true}；否则返回 {@code false}
     */
    boolean isExist(Long dictationId);

    /**
     * 分页查询指定用户的历史听写记录
     *
     * @param current 当前页码
     * @param size    页面大小
     * @param userId  用户ID
     * @return 查询到的历史听写记录
     */
    IPage<DictationVO> fetchPaginatedDictations(Long current, Long size, Long userId);

    /**
     * 查询一个听写记录
     *
     * @param dictationId 听写记录ID
     * @return 查询到的听写记录
     */
    DictationVO fetchOne(Long dictationId);
}
