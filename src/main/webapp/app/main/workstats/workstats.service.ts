import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMatchLineup } from '@/shared/model/match-lineup.model';

const baseApiLineupUrl = 'api/match-lineups';

export default class WorkstatsService {
  
  public findLineups(teamInfoId:number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseApiLineupUrl}/team-info/${teamInfoId}`).then(function(res) {
        resolve(res);
      });
    });
  }

}
