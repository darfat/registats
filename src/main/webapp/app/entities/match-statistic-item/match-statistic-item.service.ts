import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';

const baseApiUrl = 'api/match-statistic-items';
const baseSearchApiUrl = 'api/_search/match-statistic-items?query=';

export default class MatchStatisticItemService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IMatchStatisticItem> {
    return new Promise<IMatchStatisticItem>(resolve => {
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

  public create(entity: IMatchStatisticItem): Promise<IMatchStatisticItem> {
    return new Promise<IMatchStatisticItem>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMatchStatisticItem): Promise<IMatchStatisticItem> {
    return new Promise<IMatchStatisticItem>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
