import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemCacheClass {

    //private static final Logger logger = LogManager.getLogger(MemCacheClass.class);
    private static MemCachedClient client = null;

    static {
        String[] servers = { "localhost:11211" };
        SockIOPool pool = SockIOPool.getInstance("test");
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();
        client = new MemCachedClient("test");
    }

    public static boolean addInCache(String key, Object value) {
        System.out.println("cache out");
        return client.set(key, value);
       
    }

    public static Object getFromCache(String key) {
        Object value = client.get(key);
        if (value == null) {
        //    logger.info("Cache miss for key:{}", key);
            System.out.println("cache miss");
            return null;
           
        } else {
            //logger.info("Cache hit for key:{}", key);
            System.out.println("cache hit");
            return (Object) value;
        }
    }

    public static void removeFromCache(String key) {
        client.delete(key);
    //    logger.info("Removing key: {} from cache", key);
            System.out.println("cache remove");
    }

}