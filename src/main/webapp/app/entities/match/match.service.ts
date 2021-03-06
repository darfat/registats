import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMatch } from '@/shared/model/match.model';

const baseApiUrl = 'api/matches';
const baseSearchApiUrl = 'api/_search/matches?query=';

export default class MatchService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public find(id: number): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
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

  public create(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public updateStatus(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.put(`${baseApiUrl}/update-status`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public start(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.put(`${baseApiUrl}/start`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public end(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.put(`${baseApiUrl}/end`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public saveMatchStas(entity: IMatch): Promise<IMatch> {
    return new Promise<IMatch>(resolve => {
      axios.put(`${baseApiUrl}/statistics`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
  
  
}
