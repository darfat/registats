/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import MatchLineupService from '@/entities/match-lineup/match-lineup.service';
import { MatchLineup } from '@/shared/model/match-lineup.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('MatchLineup Service', () => {
    let service: MatchLineupService;
    let elemDefault;
    beforeEach(() => {
      service = new MatchLineupService();

      elemDefault = new MatchLineup(0, 0, 'AAAAAAA', false, false, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });
      it('should create a MatchLineup', async () => {
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

      it('should update a MatchLineup', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            role: 'BBBBBB',
            firstHalfPlay: true,
            secondHalfPlay: true,
            status: 'BBBBBB',
            minuteIn: 1,
            minoteOut: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });
      it('should return a list of MatchLineup', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            role: 'BBBBBB',
            firstHalfPlay: true,
            secondHalfPlay: true,
            status: 'BBBBBB',
            minuteIn: 1,
            minoteOut: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });
      it('should delete a MatchLineup', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
