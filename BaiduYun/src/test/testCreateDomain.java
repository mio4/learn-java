package test;

import com.baidubce.services.cdn.CdnClient;
import com.baidubce.services.cdn.model.CreateDomainRequest;

public class testCreateDomain {

    /**
     * 测试创建域名API
     * @param args
     */
    public static void main(String[] args){
        CreateDomainRequest createDomainRequest = new CreateDomainRequest();
        CdnClient cdnClient = new CdnClient();
        cdnClient.createDomain(createDomainRequest);
    }
}
