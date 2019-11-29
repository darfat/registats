import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPlayerHistoryStatistic } from '@/shared/model/player-history-statistic.model';

const baseApiUrl = 'api/player-history-statistics';
const baseSearchApiUrl = 'api/_search/player-history-statistics?query=';

export default class PlayerHistoryStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IPlayerHistoryStatistic> {
    return new Promise<IPlayerHistoryStatistic>(resolve => {
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

  public create(entity: IPlayerHistoryStatistic): Promise<IPlayerHistoryStatistic> {
    return new Promise<IPlayerHistoryStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPlayerHistoryStatistic): Promise<IPlayerHistoryStatistic> {
    return new Promise<IPlayerHistoryStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
