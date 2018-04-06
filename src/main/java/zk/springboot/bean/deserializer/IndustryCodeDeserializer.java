package zk.springboot.bean.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class IndustryCodeDeserializer extends JsonDeserializer<String>
{

	@Override
	public String deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
		// tse_1101.tw_20180403 -> tse_1101.tw
		return parser.getText().replaceAll( "_\\d{8}$", "" );
	}

}
