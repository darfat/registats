/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import MatchAwayInfoService from '@/entities/match-away-info/match-away-info.service';
import { MatchAwayInfo, Formation } from '@/shared/model/match-away-info.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('MatchAwayInfo Service', () => {
    let service: MatchAwayInfoService;
    let elemDefault;
    beforeEach(() => {
      service = new MatchAwayInfoService();

      elemDefault = new MatchAwayInfo(0, 'AAAAAAA', Formation.FOURFOURTWO, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a MatchAwayInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a MatchAwayInfo', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            formation: 'BBBBBB',
            analysis: 'BBBBBB',
            preMatchTalk: 'BBBBBB',
            postMatchTalk: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of MatchAwayInfo', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            formation: 'BBBBBB',
            analysis: 'BBBBBB',
            preMatchTalk: 'BBBBBB',
            postMatchTalk: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a MatchAwayInfo', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
