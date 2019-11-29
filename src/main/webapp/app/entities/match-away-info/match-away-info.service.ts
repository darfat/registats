import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';

const baseApiUrl = 'api/match-away-infos';
const baseSearchApiUrl = 'api/_search/match-away-infos?query=';

export default class MatchAwayInfoService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IMatchAwayInfo> {
    return new Promise<IMatchAwayInfo>(resolve => {
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

  public create(entity: IMatchAwayInfo): Promise<IMatchAwayInfo> {
    return new Promise<IMatchAwayInfo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMatchAwayInfo): Promise<IMatchAwayInfo> {
    return new Promise<IMatchAwayInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
