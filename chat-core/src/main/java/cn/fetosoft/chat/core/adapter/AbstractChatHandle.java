package cn.fetosoft.chat.core.adapter;

import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * 基础业务处理类
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/27 17:12
 */
public abstract class AbstractChatHandle<T extends BaseRequest, R extends BaseResponse> implements ChatService,
		InitializingBean {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractChatHandle.class);
	private Validator validator;

	/**
	 * 初始化验证器
	 */
	@Override
	public void afterPropertiesSet(){
		validator = Validation.byProvider(HibernateValidator.class)
				.configure()
				.failFast(true)
				.buildValidatorFactory()
				.getValidator();
	}

	/**
	 * 业务处理
	 *
	 * @param requestMessage
	 * @return
	 */
	@Override
	public String handler(RequestMessage requestMessage) {
		RespResult<R> result = RespResult.createSuccess();
		String output = "";
		try{
			JSONObject body = requestMessage.getData();
			T t = this.convertParam(body);
			t.setRequest(requestMessage.getRequest());
			t.setClientIp(requestMessage.getClientIp());
			RespCode respCode = this.validParams(t);
			if(RespCode.SUCCESS == respCode) {
				result = this.doHandle(t);
			}else{
				result.setStatus(respCode);
			}
		}catch (ConversionException e) {
			result.setStatus(RespCode.PARAMS_ERROR).setMessage(e.getMessage());
		}catch(Exception e){
			result.setStatus(RespCode.HANDLE_ERROR);
			logger.error("handler", e);
		}finally {
			output = result.toString();
		}
		return output;
	}

	/**
	 * 统一参数校验
	 * @author guobingbing
	 * @date 2020/9/24 19:35
	 * @param t
	 * @param groups
	 * @return void
	 */
	private RespCode validParams(T t, Class<?>... groups){
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(t, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for(ConstraintViolation<Object> constraint:  constraintViolations){
				msg.append(constraint.getMessage()).append(",");
			}
			return RespCode.PARAMS_ERROR.setMessage(msg.deleteCharAt(msg.length() - 1).toString());
		}
		return RespCode.SUCCESS;
	}

	/**
	 * 解析参数
	 * @param jsonObject
	 * @return
	 */
	protected abstract T convertParam(JSONObject jsonObject) throws ConversionException;

	/**
	 * 详细业务处理
	 * @param t
	 * @return
	 */
	protected abstract RespResult<R> doHandle(T t);

}
