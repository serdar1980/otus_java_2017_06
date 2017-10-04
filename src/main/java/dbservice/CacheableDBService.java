package dbservice;

import cacheengine.CacheEngine;
import java.util.List;
import model.DataSet;

/**
 * Created by Serdar on 04.10.2017.
 */
public class CacheableDBService implements DBService{
  private DBService dbService;
  //// TODO: 04.10.2017 ключ кэша объект в котором есть емя таблицы и id
  private CacheEngine<Long, DataSet> cacheEngine;

  public CacheableDBService(DBService dbService, CacheEngine<Long, DataSet> cacheEngine) {
    this.dbService = dbService;
    this.cacheEngine = cacheEngine;
  }


  @Override
  public String getLocalStatus() {
    return dbService.getLocalStatus();
  }

  @Override
  public void save(DataSet dataSet) {
    //// TODO: 04.10.2017 надо получать от dbService что сохранил потом класть в кэш
    dbService.save(dataSet);
    cacheEngine.put(dataSet.getId(), dataSet);
  }

  @Override
  public <T extends DataSet> DataSet read(long id, Class clazz) {
    System.out.println("Read Data from Cache");
    DataSet dataSet = cacheEngine.get(id);
    if(dataSet == null){
      System.out.println("Read Data from DB");
      dataSet  = dbService.read(id, clazz);
      if(dataSet != null){
        cacheEngine.put(id, dataSet);
      }
    }
    return (T)dataSet;
  }

  @Override
  public DataSet readByName(String name, Class clazz) {
    throw new UnsupportedOperationException("Method not supported");
  }

  @Override
  public List<? extends DataSet> readAll(Class clazz) {
    throw new UnsupportedOperationException("Method not supported");
  }

  @Override
  public void shutdown() {
    dbService.shutdown();
  }
  public int getHitCount(){
    return cacheEngine.getHitCount();
  }
  public int getMissCount(){
    return cacheEngine.getMissCount();
  }
}
