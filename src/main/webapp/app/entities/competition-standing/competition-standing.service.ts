import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompetitionStanding } from '@/shared/model/competition-standing.model';

const baseApiUrl = 'api/competition-standings';
const baseSearchApiUrl = 'api/_search/competition-standings?query=';

export default class CompetitionStandingService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<ICompetitionStanding> {
    return new Promise<ICompetitionStanding>(resolve => {
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

  public create(entity: ICompetitionStanding): Promise<ICompetitionStanding> {
    return new Promise<ICompetitionStanding>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICompetitionStanding): Promise<ICompetitionStanding> {
    return new Promise<ICompetitionStanding>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
