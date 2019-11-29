/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';
import { CompetitionStanding } from '@/shared/model/competition-standing.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('CompetitionStanding Service', () => {
    let service: CompetitionStandingService;
    let elemDefault;
    beforeEach(() => {
      service = new CompetitionStandingService();

      elemDefault = new CompetitionStanding(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a CompetitionStanding', async () => {
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

      it('should update a CompetitionStanding', async () => {
        const returnedFromService = Object.assign(
          {
            position: 1,
            played: 1,
            win: 1,
            draw: 1,
            lose: 1,
            goal: 1,
            goalAgaints: 1,
            point: 1,
            minusPoint: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of CompetitionStanding', async () => {
        const returnedFromService = Object.assign(
          {
            position: 1,
            played: 1,
            win: 1,
            draw: 1,
            lose: 1,
            goal: 1,
            goalAgaints: 1,
            point: 1,
            minusPoint: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a CompetitionStanding', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
