import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';

const baseApiUrl = 'api/player-match-statistics';
const baseSearchApiUrl = 'api/_search/player-match-statistics?query=';

export default class PlayerMatchStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IPlayerMatchStatistic> {
    return new Promise<IPlayerMatchStatistic>(resolve => {
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

  public create(entity: IPlayerMatchStatistic): Promise<IPlayerMatchStatistic> {
    return new Promise<IPlayerMatchStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPlayerMatchStatistic): Promise<IPlayerMatchStatistic> {
    return new Promise<IPlayerMatchStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
