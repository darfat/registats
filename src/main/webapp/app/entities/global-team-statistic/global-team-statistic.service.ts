import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IGlobalTeamStatistic } from '@/shared/model/global-team-statistic.model';

const baseApiUrl = 'api/global-team-statistics';
const baseSearchApiUrl = 'api/_search/global-team-statistics?query=';

export default class GlobalTeamStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IGlobalTeamStatistic> {
    return new Promise<IGlobalTeamStatistic>(resolve => {
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

  public create(entity: IGlobalTeamStatistic): Promise<IGlobalTeamStatistic> {
    return new Promise<IGlobalTeamStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IGlobalTeamStatistic): Promise<IGlobalTeamStatistic> {
    return new Promise<IGlobalTeamStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
