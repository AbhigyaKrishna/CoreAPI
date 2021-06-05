package me.Abhigya.core.util.world;

import java.io.File;

/**
 * Class for dealing with worlds
 */
public class WorldUtils {

    public static final String LEVEL_DATA_FILE_NAME = "level.dat";
    public static final String REGION_FOLDER_NAME = "region";

    public static boolean worldFolderCheck(File world_folder) {
        if (world_folder.isDirectory() && world_folder.exists()) {
            File dat = new File(world_folder, LEVEL_DATA_FILE_NAME);
            File region = new File(world_folder, REGION_FOLDER_NAME);

            return dat.exists() && region.exists();
//			return new File ( world_folder , LEVEL_DATA_FILE_NAME ).exists ( )
//					&& new File ( world_folder , REGION_FOLDER_NAME ).isDirectory ( );
        } else {
            return false;
        }
    }
}
