package goodsfrom.app.omeryasar.atry.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class CodeContract {
    public static final String CONTENT_AUTHORITY = "goodsfrom.app.omeryasar.atry";
    public static final String PATH = "codes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



    private CodeContract(){}

    public static final class CodeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH);

        public static final String _ID = BaseColumns._ID;

        public static final String TABLE_NAME =  "codes";

        public static final String COUNTRY_CODE = "country";

        public static final String Country_NAME = "count";


        public static final String COUNTRY_TRANSLATION = "translation";

        public static final String COUNTRY_FLAG_CODE  = "flag_code";


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    }

}
