import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IGlobalPlayerStatistic } from '@/shared/model/global-player-statistic.model';

const baseApiUrl = 'api/global-player-statistics';
const baseSearchApiUrl = 'api/_search/global-player-statistics?query=';

export default class GlobalPlayerStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IGlobalPlayerStatistic> {
    return new Promise<IGlobalPlayerStatistic>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IGlobalPlayerStatistic): Promise<IGlobalPlayerStatistic> {
    return new Promise<IGlobalPlayerStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IGlobalPlayerStatistic): Promise<IGlobalPlayerStatistic> {
    return new Promise<IGlobalPlayerStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
