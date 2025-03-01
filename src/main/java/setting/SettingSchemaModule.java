package setting;


public class SettingSchemaModule {

    private static SettingSchema schema;

    public SettingSchemaModule(String discorduserid) {
        schema = SettingHandler.getUserSetting(discorduserid);
    }

    public static SettingSchema getSettingSchema() {
        return schema;
    }
}
