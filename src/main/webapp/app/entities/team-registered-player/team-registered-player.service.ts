import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITeamRegisteredPlayer } from '@/shared/model/team-registered-player.model';

const baseApiUrl = 'api/team-registered-players';
const baseSearchApiUrl = 'api/_search/team-registered-players?query=';

export default class TeamRegisteredPlayerService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<ITeamRegisteredPlayer> {
    return new Promise<ITeamRegisteredPlayer>(resolve => {
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

  public create(entity: ITeamRegisteredPlayer): Promise<ITeamRegisteredPlayer> {
    return new Promise<ITeamRegisteredPlayer>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITeamRegisteredPlayer): Promise<ITeamRegisteredPlayer> {
    return new Promise<ITeamRegisteredPlayer>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
