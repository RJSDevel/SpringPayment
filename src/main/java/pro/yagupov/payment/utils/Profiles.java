package pro.yagupov.payment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Yagupov Ruslan on 22.12.16.
 */
@Component
public class Profiles {

    @Autowired
    private Environment mEnv;


    public String getDatasourceDriverClassName() {
        return mEnv.getProperty("spring.datasource.driverClassName");
    }

    public String getDatasourceUrl() {
        return mEnv.getProperty("spring.datasource.url");
    }

    public String getDatasourceUsername() {
        return mEnv.getProperty("spring.datasource.username");
    }

    public String getDatasourcePassword() {
        return mEnv.getProperty("spring.datasource.password");
    }

    public String getPackagesToScan() {
        return mEnv.getProperty("entitymanager.packagesToScan");
    }

    public int getBatchChunkSize() {
        return mEnv.getProperty("batch.chunkSize", Integer.TYPE);
    }
}
