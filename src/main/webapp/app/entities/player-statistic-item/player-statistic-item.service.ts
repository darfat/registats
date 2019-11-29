import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

const baseApiUrl = 'api/player-statistic-items';
const baseSearchApiUrl = 'api/_search/player-statistic-items?query=';

export default class PlayerStatisticItemService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IPlayerStatisticItem> {
    return new Promise<IPlayerStatisticItem>(resolve => {
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

  public create(entity: IPlayerStatisticItem): Promise<IPlayerStatisticItem> {
    return new Promise<IPlayerStatisticItem>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPlayerStatisticItem): Promise<IPlayerStatisticItem> {
    return new Promise<IPlayerStatisticItem>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
