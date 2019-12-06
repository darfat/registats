import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMatchTeamInfo } from '@/shared/model/match-team-info.model';

const baseApiUrl = 'api/match-team-infos';
const baseSearchApiUrl = 'api/_search/match-team-infos?query=';

export default class MatchHomeInfoService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IMatchTeamInfo> {
    return new Promise<IMatchTeamInfo>(resolve => {
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

  public create(entity: IMatchTeamInfo): Promise<IMatchTeamInfo> {
    return new Promise<IMatchTeamInfo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMatchTeamInfo): Promise<IMatchTeamInfo> {
    return new Promise<IMatchTeamInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public createTeamInfo(entity: IMatchTeamInfo): Promise<IMatchTeamInfo> {
    return new Promise<IMatchTeamInfo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public updateTeamInfo(entity: IMatchTeamInfo): Promise<IMatchTeamInfo> {
    return new Promise<IMatchTeamInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
