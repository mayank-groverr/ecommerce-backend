package practice.mayank.ecommerce.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import practice.mayank.ecommerce.exception.customexception.InvalidPatchOperationException;


public class PatchUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private PatchUtil(){}


    public static <T> T applyJsonPatch(JsonPatch jsonPatch, T valueToBePatched, Class<T> returnValue){
        try{
            JsonNode jsonNode = objectMapper.valueToTree(valueToBePatched);
            JsonNode afterPatched = jsonPatch.apply(jsonNode);
            return objectMapper.treeToValue(afterPatched, returnValue);
        }catch (JsonPatchException | JsonProcessingException e){
            throw new InvalidPatchOperationException(e.getMessage());
        }
    }


}
