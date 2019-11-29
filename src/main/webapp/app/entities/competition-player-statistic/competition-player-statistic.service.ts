import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompetitionPlayerStatistic } from '@/shared/model/competition-player-statistic.model';

const baseApiUrl = 'api/competition-player-statistics';
const baseSearchApiUrl = 'api/_search/competition-player-statistics?query=';

export default class CompetitionPlayerStatisticService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<ICompetitionPlayerStatistic> {
    return new Promise<ICompetitionPlayerStatistic>(resolve => {
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

  public create(entity: ICompetitionPlayerStatistic): Promise<ICompetitionPlayerStatistic> {
    return new Promise<ICompetitionPlayerStatistic>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICompetitionPlayerStatistic): Promise<ICompetitionPlayerStatistic> {
    return new Promise<ICompetitionPlayerStatistic>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
