import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompetitionTeamStatistic } from '@/shared/model/competition-team-statistic.model';

const baseApiUrl = 'api/competition-team-statistics';
const baseSearchApiUrl = 'api/_search/competition-team-statistics?query=';

export default class CompetitionTeamStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<ICompetitionTeamStatistic> {
    return new Promise<ICompetitionTeamStatistic>(resolve => {
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

  public create(entity: ICompetitionTeamStatistic): Promise<ICompetitionTeamStatistic> {
    return new Promise<ICompetitionTeamStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICompetitionTeamStatistic): Promise<ICompetitionTeamStatistic> {
    return new Promise<ICompetitionTeamStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
