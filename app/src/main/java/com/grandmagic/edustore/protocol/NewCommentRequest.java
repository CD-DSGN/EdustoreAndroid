package com.grandmagic.edustore.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lps on 2017/3/8.
 */

public class NewCommentRequest {
   public String target_comment_id;
    public  String news_id;
    public String comment_content;
    public SESSION mSESSION;

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        if (null != mSESSION) {
            try {
                result.put("session", mSESSION.toJson());
                result.put("comment_content", comment_content);
                result.put("news_id", news_id);
                if (target_comment_id != null)
                    result.put("target_comment_id", target_comment_id);
            } catch (JSONException mE) {
                mE.printStackTrace();
            }
        }
        return result;
    }
}
