package setting;

/**
 * The setting module representation that modules that requirement must extend
 */
public class SettingSchemaModule {

    private static SettingSchema schema;

    public SettingSchemaModule(String discorduserid) {
        schema = SettingHandler.getUserSetting(discorduserid);
    }

    /**
     * gets the setting schema
     * @return the setting schema object
     */
    public static SettingSchema getSettingSchema() {
        return schema;
    }
}
