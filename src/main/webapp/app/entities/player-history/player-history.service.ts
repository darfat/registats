import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPlayerHistory } from '@/shared/model/player-history.model';

const baseApiUrl = 'api/player-histories';
const baseSearchApiUrl = 'api/_search/player-histories?query=';

export default class PlayerHistoryService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IPlayerHistory> {
    return new Promise<IPlayerHistory>(resolve => {
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

  public create(entity: IPlayerHistory): Promise<IPlayerHistory> {
    return new Promise<IPlayerHistory>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPlayerHistory): Promise<IPlayerHistory> {
    return new Promise<IPlayerHistory>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
